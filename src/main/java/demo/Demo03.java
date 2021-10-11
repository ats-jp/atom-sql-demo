package demo;

import java.sql.Timestamp;
import java.util.function.Consumer;

import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlParameters;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo03 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.insert(p -> {
				p.id = 999;
				p.name1 = "name1";
				p.name2 = "name2";
				p.companyId = null;
				p.postalCode = "000-0000";
				p.addr1 = "addr1";
				p.addr2 = "addr2";
				p.addr3 = "addr3";
				p.tel1 = "00-000-0000";
				p.tel2 = "00-000-0000";
				p.cellular = "090-0000-0000";
				p.created = new Timestamp(System.currentTimeMillis());
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("""
			INSERT INTO customer VALUES (
				:id/*P_LONG*/,
				:name1/*STRING*/,
				:name2/*STRING*/,
				:companyId/*LONG*/,
				:postalCode/*STRING*/,
				:addr1/*STRING*/,
				:addr2/*STRING*/,
				:addr3/*STRING*/,
				:tel1/*STRING*/,
				:tel2/*STRING*/,
				:cellular/*STRING*/,
				:created/*TIMESTAMP*/)
						""")
		@SqlParameters("Demo03Parameters")
		int insert(Consumer<Demo03Parameters> c);
	}
}
