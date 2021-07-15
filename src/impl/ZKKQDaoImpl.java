package impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.zkkq.dao.ZKKQDao;

public class ZKKQDaoImpl extends JdbcDaoSupport implements ZKKQDao {
	
	@Override
	public List<Map<String, Object>> listEquips() {
		String sql = "select t.equip_num num,t.equip_ip ip,t.equip_port port from kq_equip t where t.equip_state=1 order by t.id";
		return getJdbcTemplate().queryForList(sql);
	}
	
	public int searchRec(String enrollNumber,String signTime){
		String sql = "select count(*) from kq_basic_signrec where enrollnumber=? and signtime=?";
		return getJdbcTemplate().queryForInt(sql,new Object[]{enrollNumber,signTime});
	}
	
	@Override
	public void insertRec(String enrollNumber, String signTime, int inOutMode,
			int verifyMode) {
		String sql = "insert into kq_basic_signrec(id,enrollnumber,signtime,inoutmode,verifymode,flag) values(seq_kq_basic_signrec.nextval,?,?,?,?,0)";
		getJdbcTemplate().update(
				sql,
				new Object[] { enrollNumber, signTime, inOutMode, verifyMode },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER,Types.INTEGER }
			);
	}
}
