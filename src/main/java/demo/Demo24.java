package demo;

import java.util.Optional;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo24 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			Sandbox.resultSet(r -> {
				r.setColumnNames("column1", "column2");

				r.addRow("1", "2");
				r.addRow("1", "2");
			});

			proxy.select().put(proxy.select1(), proxy.select2()).list().forEach(o -> {
				System.out.println(o.column1);
				System.out.println(o.column2);
			});

			var atom = proxy.select2();//select1だとコンパイルエラー
			Optional.empty().map(i -> atom).orElse(atom);
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer /*${0}*//*${1}*/")
		Atom<DataObject1> select();

		@Sql(" -- 1")
		Atom<?> select1();

		@Sql(" -- 2")
		Atom<Void> select2();

		@DataObject
		public static record DataObject1(
			String column1,
			String column2) {}
	}
}
