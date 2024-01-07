package demo;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import jp.ats.atomsql.AtomSql;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.AtomSqlSupplier;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;
import jp.ats.atomsql.annotation.SqlProxySupplier;

public class Demo12 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.atomSql().of(Proxy.class).insert(p -> {
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
				p.created = LocalDateTime.now();
			});

			proxy.proxy().insert(p -> {
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
				p.created = LocalDateTime.now();
			});
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql(/*@formatter:off*/"""
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
	:created/*DATETIME*/)
		"""/*@formatter:on*/)
		int insert(Consumer<Demo12Parameters> c);

		@AtomSqlSupplier
		AtomSql atomSql();

		@SqlProxySupplier
		Proxy proxy();
	}
}
