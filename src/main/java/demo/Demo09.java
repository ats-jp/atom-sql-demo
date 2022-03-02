package demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo09 {

	private static Atom<?> WHERE = Atom.newStaticInstance(a -> a.of(ProxyForStatic.class).where());

	private static Atom<?> CONDITION = Atom.newStaticInstance(a -> a.of(ProxyForStatic.class).condition(9999));

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.list();
		});
	}

	@SqlProxy
	public interface ProxyForStatic {

		@Sql("WHERE")
		Atom<?> where();

		@Sql("id = :id")
		Atom<?> condition(int id);
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer")
		Atom<DataObjectImpl> select();

		default List<DataObjectImpl> list() {
			return select().concat(WHERE, CONDITION).list();
		}
	}

	@DataObject
	public class DataObjectImpl {

		public final long id;

		public final String name;

		public final LocalDateTime created;

		public DataObjectImpl(ResultSet result) throws SQLException {
			id = result.getLong("id");
			name = result.getString("name");
			created = result.getTimestamp("created").toLocalDateTime();
		}
	}
}
