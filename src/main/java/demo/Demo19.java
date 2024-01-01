package demo;

import java.util.List;
import java.util.Optional;

import jp.ats.atomsql.Csv;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.EnumValue;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo19 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.selectByEnum1(Enum1.CONST2);

			proxy.selectByEnum2(Enum2.CONST3);

			proxy.selectByEnum1(null);

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

		@DataObject
		public static class DataObjectImpl {

			public Enum1 e1;

			public Enum2 e2;
		}
	}
}
