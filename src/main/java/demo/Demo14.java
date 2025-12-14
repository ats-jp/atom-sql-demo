package demo;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo14 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			var main = proxy.insert();

			main.put(proxy.for0(), proxy.for1()).put("last", proxy.forLast()).execute();
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql(/*@formatter:off*/"""
INSERT INTO customer VALUES (
/*${0}*/,
/*${1}*/,
/*${last}*/
)
		"""/*@formatter:on*/)
		Atom<?> insert();

		@Sql("id")
		Atom<?> for0();

		@Sql("name")
		Atom<?> for1();

		@Sql("created_at")
		Atom<?> forLast();
	}
}
