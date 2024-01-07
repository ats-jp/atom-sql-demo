package demo;

import java.util.function.Consumer;

import jp.ats.atomsql.AtomSql;
import jp.ats.atomsql.AtomSqlType;
import jp.ats.atomsql.Csv;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.AtomSqlSupplier;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;
import jp.ats.atomsql.annotation.SqlProxySupplier;
import jp.ats.atomsql.annotation.TypeHint;
import jp.ats.atomsql.annotation.TypeHints;

public class Demo17 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.atomSql().of(Proxy.class).insert(p -> {
				p.name = "name";
				p.ids = Csv.of(1L, 2L, 3L);
				p.names = Csv.of("name1", "name2");
			});

			proxy.proxy().insert(p -> {
				p.name = "name";
				p.ids = Csv.of(1L, 2L, 3L);
				p.names = Csv.of("name1", "name2");
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("UPDATE customer SET name = :name WHERE id IN (:ids) AND name IN (:names)")
		@TypeHints({
			@TypeHint(name = "name", type = AtomSqlType.STRING),
			@TypeHint(name = "ids", type = AtomSqlType.CSV, typeArgument = AtomSqlType.P_LONG),
			@TypeHint(name = "names", type = AtomSqlType.CSV, typeArgument = AtomSqlType.STRING)
		})
		void insert(Consumer<Demo17Parameters> c);

		@AtomSqlSupplier
		AtomSql atomSql();

		@SqlProxySupplier
		Proxy proxy();
	}
}
