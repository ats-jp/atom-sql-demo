package demo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import jp.ats.atomsql.Atom;
import jp.ats.atomsql.Protoatom;
import jp.ats.atomsql.Sandbox;
import jp.ats.atomsql.annotation.Sql;
import jp.ats.atomsql.annotation.SqlProxy;

public class Demo26 {

	public static void main(String[] args) throws Exception {
		Sandbox.execute(atomSql -> {
			var proxy = atomSql.of(Proxy.class);

			proxy.selectAtom();

			proxy.selectProtoatom();
		});
	}

	@SqlProxy
	public interface Proxy {

		@Sql("""
			SELECT
			  test1/*STRING*/,
			  test2/*Demo26.MyEnum*/
			FROM customer
			""")
		List<Demo26_ListDataObject> selectList();

		@Sql("""
			SELECT
			  test1/*STRING*/,
			  test2/*Demo26.MyEnum*/
			FROM customer
			""")
		Optional<Demo26_OptionalDataObject> selectOptional();

		@Sql("""
			SELECT
			  test1/*STRING*/,
			  test2/*Demo26.MyEnum*/
			FROM customer
			""")
		Stream<Demo26_StreamDataObject> selectStream();

		@Sql("""
			SELECT
			  test1/*STRING*/,
			  test2/*Demo26.MyEnum*/
			FROM customer
			""")
		Atom<Demo26_AtomDataObject> selectAtom();

		@Sql("""
			SELECT
			  test1/*STRING*/,
			  test2/*Demo26.MyEnum*/
			FROM customer
			""")
		Protoatom<Demo26_ProtoatomDataObject, Demo26_Unfolder> selectProtoatom();

		@Sql("""
			SELECT
			  1
			FROM customer
			""")
		Protoatom<Integer, Demo26_Unfolder> selectPrimitiveProtoatom();
	}

	public static enum MyEnum {

		ENUM_A,

		ENUM_B;
	}
}
