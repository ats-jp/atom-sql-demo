package demo;

import java.util.List;

import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.OptionalColumn;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo23 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			Sandbox.resultSet(r -> {
				r.setColumnNames("column1", "column2");

				r.addRow("1", "2");
				r.addRow("1", "2");
			});

			proxy.select1().forEach(o -> {
				System.out.println(o.column1);
				System.out.println(o.column2);
			});

			proxy.select2().forEach(o -> {
				System.out.println(o.column1);
				System.out.println(o.column2);
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer")
		List<DataObject1> select1();

		@DataObject
		public static class DataObject1 {

			public String column1;

			public String column2;

			@OptionalColumn
			public String column3;
		}

		@Sql("SELECT * FROM customer")
		List<DataObject2> select2();

		@DataObject
		public static record DataObject2(
			String column1,
			String column2,
			@OptionalColumn String column3) {}
	}
}
