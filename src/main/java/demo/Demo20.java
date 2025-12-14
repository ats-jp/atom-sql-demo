package demo;

import java.util.List;
import java.util.function.Consumer;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Protoatom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo20 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			Sandbox.resultSet(r -> {
				r.addRow(91);
				r.addRow(92);
				r.addRow(93);
			});

			proxy.select1().forEach(System.out::println);

			proxy.select2().list().forEach(System.out::println);

			proxy.select3(p -> p.id = 1).put(i -> i.select = proxy.select2()).list().forEach(System.out::println);
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT 1 FROM customer")
		List<Integer> select1();

		@Sql("SELECT 1 FROM customer")
		Atom<Integer> select2();

		@Sql("SELECT 1 FROM customer /*${select}*/ WHERE id = :id")
		Protoatom<Integer, Demo20_Proxy_select3> select3(Consumer<Demo20_Proxy_select4> c);
	}
}
