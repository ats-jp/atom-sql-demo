package demo;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlParameters;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo18 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.selectByInteger(null);

			proxy.selectByLong(null);

			proxy.insert(null, null, null);

			proxy.insert(p -> {
				p.id = null;
				p.name = null;
				p.created = null;
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer WHERE id = :id")
		Optional<DataObjectImpl> selectByInteger(Integer id);

		@Sql("SELECT * FROM customer WHERE id = :id")
		Optional<DataObjectImpl> selectByLong(Long id);

		@Sql("SELECT * FROM customer WHERE id = :id")
		Optional<DataObjectImpl> selectByString(String id);

		@Sql("INSERT INTO customer (id, name, created) VALUES (:id, :name, :created)")
		int insert(Long id, String name, LocalDateTime created);

		@Sql("INSERT INTO customer (id, name, created) VALUES (:id, :name, :created)")
		@SqlParameters
		int insert(Consumer<Demo18_P1> c);

		@DataObject
		public static class DataObjectImpl {

			public long id;

			public String name;

			public LocalDateTime created;
		}
	}
}
