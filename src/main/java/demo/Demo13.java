package demo;

import java.time.LocalDateTime;
import java.util.Optional;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo13 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			var atom = proxy.selectAsAtom(999);

			atom.apply(DataObjectImpl.class).list();
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer WHERE id = :id")
		Atom<?> selectAsAtom(long id);
	}

	@DataObject
	public static class DataObjectImpl {

		public long id;

		public String name;

		public Optional<LocalDateTime> created;
	}
}
