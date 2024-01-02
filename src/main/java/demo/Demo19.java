package demo;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import jp.ats.atomsql.Csv;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.EnumValue;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlParameters;
import jp.ats.atomsql.annotation.SqlProxy;
import jp.ats.atomsql.annotation.TypeHint;

public class Demo19 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.selectByEnum1(Enum1.CONST2);

			proxy.selectByEnum2(Enum2.CONST3);

			proxy.selectByEnum1(null);

			proxy.selectByParameters(p -> p.type = Enum1.CONST1);

			proxy.selectByParameters(p -> p.type = null);

			proxy.selectByCsv(p -> p.types = Csv.of(Enum1.CONST1));

			proxy.selectWithHint(p -> {
				p.type = Enum1.CONST1;
				p.csv = Csv.of(Enum1.CONST1, Enum1.CONST3);
			});

			Sandbox.resultSet(r -> {
				r.setColumnNames("e1", "e2");

				r.addRow(91, 0);
				r.addRow(92, 1);
				r.addRow(93, 2);
			});

			proxy.selectByCsv(Csv.of(Enum1.CONST1, Enum1.CONST2)).forEach(o -> {
				System.out.println(o.e1 + " " + o.e2);
			});
		});
	}

	public static enum Enum1 {

		@EnumValue(91)
		CONST1,

		@EnumValue(92)
		CONST2,

		@EnumValue(93)
		CONST3,
	}

	public static enum Enum2 {

		CONST1,

		CONST2,

		CONST3,
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer WHERE type = :type")
		Optional<DataObjectImpl> selectByEnum1(Enum1 type);

		@Sql("SELECT * FROM customer WHERE type = :type")
		Optional<DataObjectImpl> selectByEnum2(Enum2 type);

		@Sql("SELECT * FROM customer WHERE type IN (:types)")
		List<DataObjectImpl> selectByCsv(Csv<Enum1> types);

		@Sql("SELECT * FROM customer WHERE type = :type/*Demo19.Enum1*/")
		@SqlParameters
		List<DataObjectImpl> selectByParameters(Consumer<Demo19_P1> c);

		@Sql("SELECT * FROM customer WHERE type IN (:types/*CSV<Demo19.Enum1>*/)")
		@SqlParameters
		List<DataObjectImpl> selectByCsv(Consumer<Demo19_P2> c);

		@Sql("SELECT * FROM customer WHERE type = :type AND csv IN :csv")
		@SqlParameters(typeHints = {
			@TypeHint(name = "type", type = "Demo19.Enum1"),
			@TypeHint(name = "csv", type = "CSV", typeArgument = "Demo19.Enum1"),
		})
		List<DataObjectImpl> selectWithHint(Consumer<Demo19_P3> c);

		@DataObject
		public static class DataObjectImpl {

			public Enum1 e1;

			public Enum2 e2;
		}
	}
}
