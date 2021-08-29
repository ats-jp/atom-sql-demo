package demo;

import java.sql.Timestamp;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.DataObject;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo05 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			build(proxy, null, null, false, false, false, false).list();

			build(proxy, 999L, null, false, false, false, false).list();

			build(proxy, null, "%name%", false, false, false, false).list();

			build(proxy, null, "%name%", true, false, true, false).list();

			build(proxy, null, "%name%", false, true, false, false).list();

			build(proxy, null, "%name%", false, true, true, true).list();

			build(proxy, null, null, true, true, false, true).list();

			build(proxy, null, "%name%", false, false, false, true).list();
		});
	}

	private static Atom<?> build(
		Proxy proxy,
		Long id,
		String name,
		boolean type1,
		boolean type2,
		boolean type3,
		boolean useUpdated) {
		var condition = proxy.blank();

		if (id != null)
			condition = condition.and(proxy.idCondition(id));

		if (name != null)
			condition = condition.and(proxy.nameCondition(name));

		var typeCondition = proxy.blank();

		if (type1)
			typeCondition = typeCondition.or(proxy.customerTypeCondition1());

		if (type2)
			typeCondition = typeCondition.or(proxy.customerTypeCondition2());

		if (type3)
			typeCondition = typeCondition.or(proxy.customerTypeCondition3());

		condition = condition.and(typeCondition);

		if (useUpdated)
			condition = condition.and(proxy.updatedCondition());

		return !condition.isEmpty() ? proxy.main().concat(proxy.where()).concat(condition) : proxy.main();
	}

	@SqlProxy
	public interface Proxy {

		@Sql("SELECT * FROM customer")
		Atom<DataObjectImpl> main();

		@Sql("")
		Atom<?> blank();

		@Sql("WHERE")
		Atom<?> where();

		@Sql("id = :id")
		Atom<?> idCondition(Long id);

		@Sql("name LIKE :name")
		Atom<?> nameCondition(String name);

		@Sql("addr1 = :addr1")
		Atom<?> addr1Condition(long id);

		@Sql("customer_type = 1")
		Atom<?> customerTypeCondition1();

		@Sql("customer_type = 2")
		Atom<?> customerTypeCondition2();

		@Sql("customer_type = 3")
		Atom<?> customerTypeCondition3();

		@Sql("updated IS NULL")
		Atom<?> updatedCondition();

		@DataObject
		public static class DataObjectImpl {

			public long id;

			public String name;

			public Timestamp created;
		}
	}
}
