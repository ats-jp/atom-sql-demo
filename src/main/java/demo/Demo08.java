package demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo08 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			atomSql.tryNonThreadSafe(() -> {
				proxy.selectById(new byte[] { 0 });
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer WHERE id = :id")
		Optional<DataObjectImpl> selectById(byte[] id);
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
