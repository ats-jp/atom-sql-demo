package demo;

import java.util.List;
import java.util.function.Consumer;

import jp.ats.atomsql.AtomSql;
import jp.ats.atomsql.Csv;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo17 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.atomSql().of(Proxy.class).select(p -> {
				p.name = "name";
				p.ids = Csv.of(1L, 2L, 3L);
				p.names = Csv.of("name1", "name2");
			});

			proxy.proxy().select(p -> {
				p.name = "name";
				p.ids = Csv.of(1L, 2L, 3L);
				p.names = Csv.of("name1", "name2");
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT 1 FROM test WHERE  name = :name/*STRING*/ AND id IN (:ids/*CSV<P_LONG>*/) AND name IN (:names/*CSV<STRING>*/)")
		List<Integer> select(Consumer<Demo17Parameters> c);

		AtomSql atomSql();

		Proxy proxy();
	}
}
