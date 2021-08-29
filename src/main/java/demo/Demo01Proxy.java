package demo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

@SqlProxy
public interface Demo01Proxy {

	@Sql("SELECT * FROM customer")
	List<DataObjectImpl> selectAsList();

	Stream<DataObjectImpl> selectAsStream();

	@Sql("SELECT * FROM customer WHERE id = :id")
	Optional<DataObjectImpl> selectById(long id);

	@Sql("SELECT * FROM customer")
	Atom<DataObjectImpl> selectAsAtom();

	@Sql("INSERT INTO customer (id, name, created) VALUES (:id, :name, :created)")
	int insert(long id, String name, Timestamp created);

	@DataObject
	public static class DataObjectImpl {

		public long id;

		public String name;

		public Timestamp created;
	}
}
