package demo;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlParameters;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo11 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.update(p -> {
				p.id = 999;
				p.name1 = "name1";
				p.name2 = "name2";
				p.companyId = null;
				p.postalCode = "000-0000";
				p.created = LocalDateTime.now();
			}).update();

			var addrAndTel = proxy.addrAndTel(p -> {
				p.addr1 = "addr1";
				p.addr2 = "addr2";
				p.addr3 = "addr3";
				p.tel1 = "00-000-0000";
				p.tel2 = "00-000-0000";
				p.cellular = "090-0000-0000";
				p.マルチバイト文字 = "OK";
			});

			proxy.update(p -> {
				p.id = 999;
				p.name1 = "name1";
				p.name2 = "name2";
				p.companyId = null;
				p.postalCode = "000-0000";
				p.created = LocalDateTime.now();
			}).format(addrAndTel).update();
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql(/*@formatter:off*/"""
UPDATE customer SET
	id = :id/*P_LONG*/,
	name1 = :name1/*STRING*/,
	name2 = :name2/*STRING*/,
	company_id = :companyId/*LONG*/,
	postal_code = :postalCode/*STRING*/,
	/*[0]*/
	created = :created/*DATETIME*/)
		"""/*@formatter:on*/)
		@SqlParameters("Demo11ParametersMain")
		Atom<?> update(Consumer<Demo11ParametersMain> c);

		@Sql(/*@formatter:off*/"""
/*インデント削除除けコメント*/
	addr1 = :addr1/*STRING*/,
	addr2 = :addr2/*STRING*/,
	addr3 = :addr3/*STRING*/,
	tel1 = :tel1/*STRING*/,
	tel2 = :tel2/*STRING*/,
	cellular = :cellular/*STRING*/,
	memo = :マルチバイト文字/*STRING*/,
		"""/*@formatter:on*/)
		@SqlParameters("Demo11ParametersSub")
		Atom<?> addrAndTel(Consumer<Demo11ParametersSub> c);
	}
}
