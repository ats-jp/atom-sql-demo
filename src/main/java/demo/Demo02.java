package demo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlFile;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo02 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.selectAsList();

			proxy.selectAsStream();

			proxy.selectAsAtom();

			proxy.selectById(999);

			proxy.insert(999, "demo customer", LocalDateTime.now());
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer")
		List<DataObjectImpl> selectAsList();

		@SqlFile
		Stream<DataObjectImpl> selectAsStream();

		@Sql("SELECT * FROM customer WHERE id = :id")
		Optional<DataObjectImpl> selectById(long id);

		@Sql("SELECT * FROM customer")
		Atom<DataObjectImpl> selectAsAtom();

		@Sql("INSERT INTO customer (id, name, created) VALUES (:id, :name, :created)")
		int insert(long id, String name, LocalDateTime created);

		@DataObject
		public static class DataObjectImpl {

			public long id;

			public String name;

			public LocalDateTime created;
		}
	}
}
