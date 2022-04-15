package demo;

import java.time.LocalDateTime;
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
	Optional<DataObjectRecord> selectById(long id);

	@Sql("SELECT * FROM customer")
	Atom<DataObjectImpl> selectAsAtom();

	@Sql("INSERT INTO customer (id, name, created) VALUES (:id, :name, :created)")
	int insert(long id, String name, LocalDateTime created);

	//defaultは対象外
	default int testDefault() {
		return 0;
	}

	@DataObject
	public static class DataObjectImpl {

		public long id;

		public String name;

		public Optional<LocalDateTime> created;

		@Override
		public String toString() {
			return DataObjectImpl.class.getSimpleName() + " [id: " + id + ", name: " + name + ", created: " + created + "]";
		}
	}

	@DataObject
	public static record DataObjectRecord(long id, String name, Optional<LocalDateTime> created) {

		@Override
		public String toString() {
			return DataObjectImpl.class.getSimpleName() + " [id: " + id + ", name: " + name + ", created: " + created + "]";
		}
	}
}
