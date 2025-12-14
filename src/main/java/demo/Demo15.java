package demo;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Protoatom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo15 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			var main = proxy.insert();

			main.put(a -> {
				a.id = proxy.forId();
				a.name = proxy.forName();
				a.created_at = proxy.forCreatedAt();
			}).execute();
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql(/*@formatter:off*/"""
INSERT INTO customer VALUES (
/*${id}*/,
/*${name}*/,
/*${created_at}*/
)
		"""/*@formatter:on*/)
		Protoatom<?, Demo15_Proxy_insert> insert();

		@Sql("id")
		Atom<?> forId();

		@Sql("name")
		Atom<?> forName();

		@Sql("created_at")
		Atom<?> forCreatedAt();
	}
}
