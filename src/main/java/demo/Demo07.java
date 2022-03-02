package demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo07 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.selectAsList();

			proxy.selectAsStream();

			proxy.selectAsAtom();

			proxy.selectById(999);
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer")
		List<DataObjectImpl> selectAsList();

		@Sql("SELECT * FROM customer")
		Stream<DataObjectImpl> selectAsStream();

		@Sql("SELECT * FROM customer WHERE id = :id")
		Optional<DataObjectImpl> selectById(long id);

		@Sql("SELECT * FROM customer")
		Atom<DataObjectImpl> selectAsAtom();
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
