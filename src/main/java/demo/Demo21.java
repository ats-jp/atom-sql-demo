package demo;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import jp.ats.atomsql.Csv;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;
import jp.ats.atomsql.annotation.StringEnum;
import jp.ats.atomsql.annotation.StringEnumValue;

public class Demo21 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.selectByEnum1(Enum1.CONST2);

			proxy.selectByEnum2(Enum2.CONST5);

			proxy.selectByEnum1(null);

			proxy.selectByParameters(p -> p.type = Enum1.CONST1);

			proxy.selectByParameters(p -> p.type = null);

			proxy.selectByCsv(p -> p.types = Csv.of(Enum1.CONST1));

			Sandbox.resultSet(r -> {
				r.setColumnNames("e1", "e2");

				r.addRow("c1", "CONST4");
				r.addRow("c2", "CONST5");
				r.addRow("c3", "CONST6");
			});

			proxy.selectByCsv(Csv.of(Enum1.CONST1, Enum1.CONST2)).forEach(o -> {
				System.out.println(o.e1 + " " + o.e2);
			});
		});
	}

	@StringEnum
	public static enum Enum1 {

		@StringEnumValue("c1")
		CONST1,

		@StringEnumValue("c2")
		CONST2,

		@StringEnumValue("c3")
		CONST3,
	}

	@StringEnum
	public static enum Enum2 {

		CONST4,

		CONST5,

		CONST6,
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer WHERE type = :type")
		Optional<DataObjectImpl> selectByEnum1(Enum1 type);

		@Sql("SELECT * FROM customer WHERE type = :type")
		Optional<DataObjectImpl> selectByEnum2(Enum2 type);

		@Sql("SELECT * FROM customer WHERE type IN (:types)")
		List<DataObjectImpl> selectByCsv(Csv<Enum1> types);

		@Sql("SELECT * FROM customer WHERE type = :type/*Demo21.Enum1*/")
		List<DataObjectImpl> selectByParameters(Consumer<Demo21_P1> c);

		@Sql("SELECT * FROM customer WHERE type IN (:types/*CSV<Demo21.Enum1>*/)")
		List<DataObjectImpl> selectByCsv(Consumer<Demo21_P2> c);

		@DataObject
		public static class DataObjectImpl {

			public Enum1 e1;

			public Enum2 e2;
		}
	}
}
