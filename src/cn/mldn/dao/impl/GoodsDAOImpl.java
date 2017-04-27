package cn.mldn.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.mldn.dao.IGoodsDAO;
import cn.mldn.util.dao.abs.AbstractDAO;
import cn.mldn.vo.Goods;

public class GoodsDAOImpl extends AbstractDAO implements IGoodsDAO {

	@Override
	public boolean doCreate(Goods vo) throws SQLException {
		String sql = "insert into goods(gid,name,price,photo,iid) values(goods_seq.nextval,?,?,?,?)";
		super.psmt = super.conn.prepareStatement(sql);
		super.psmt.setString(1,vo.getName());
		super.psmt.setDouble(2,vo.getPrice());
		super.psmt.setString(3, vo.getPhoto());
		super.psmt.setInt(4, vo.getIid());
		return super.psmt.executeUpdate()>0;
	}
	public Goods findByName(String name) throws SQLException {
		String sql = "select gid,name,price,photo,iid from goods where name=? ";
		super.psmt = super.conn.prepareStatement(sql);
		super.psmt.setString(1, name);
		ResultSet rs = super.psmt.executeQuery();
		if(rs.next()){
			Goods vo = new Goods();
			vo.setGid(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getDouble(3));
			vo.setPhoto(rs.getString(4));
			vo.setIid(rs.getInt(5));
			return vo;
		}
		return null;
	}
	public Integer findCreateId() throws SQLException {
		String sql = "select goods_seq.currval from dual ";
		super.psmt = super.conn.prepareStatement(sql);
		ResultSet rs = super.psmt.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}


	@Override
	public boolean doUpdate(Goods vo) throws SQLException {
		String sql = "update goods set name=?,price=?,iid=? where gid=? ";
		super.psmt = super.conn.prepareStatement(sql);
		super.psmt.setString(1,vo.getName());
		super.psmt.setDouble(2,vo.getPrice());

		super.psmt.setInt(3, vo.getIid());
		super.psmt.setInt(4, vo.getGid());
		return super.psmt.executeUpdate()>0;
	}

	@Override
	public boolean doRemove(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Goods findById(Integer id) throws SQLException {
		String sql = "select gid,name,price,photo,iid from goods where gid=? ";
		super.psmt = super.conn.prepareStatement(sql);
		super.psmt.setInt(1, id);
		ResultSet rs = super.psmt.executeQuery();
		if(rs.next()){
			Goods vo = new Goods();
			vo.setGid(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getDouble(3));
			vo.setPhoto(rs.getString(4));
			vo.setIid(rs.getInt(5));
			return vo;
		}
		return null;
	}

	@Override
	public List<Goods> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goods> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		List<Goods> all = new ArrayList<Goods>();
		String sql = "select* from( "
				+" select gid,name,price,photo,iid,ROWNUM rn "
				+ " from goods where ROWNUM<=?)temp where temp.rn>? ";
		super.psmt = super.conn.prepareStatement(sql);
		super.psmt.setInt(1, currentPage*lineSize);
		super.psmt.setInt(2, (currentPage-1)*lineSize);
		ResultSet rs = super.psmt.executeQuery();
		while(rs.next()){
			Goods vo = new Goods();
			vo.setGid(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getDouble(3));
			vo.setPhoto(rs.getString(4));
			vo.setIid(rs.getInt(5));
			all.add(vo);
			return all;
		}
		return null;
	}

	@Override
	public List<Goods> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord)
			throws SQLException {
		List<Goods> all = new ArrayList<Goods>();
		String sql = " select* from( "
				+" select gid,name,price,photo,iid,ROWNUM rn "
				+ " from goods where "+column+" like? and ROWNUM<=?)temp where temp.rn>? ";
		super.psmt = super.conn.prepareStatement(sql);
		super.psmt.setString(1, "%"+keyWord+"%");
		super.psmt.setInt(2, currentPage*lineSize);
		super.psmt.setInt(3, (currentPage-1)*lineSize);
		ResultSet rs = super.psmt.executeQuery();
		while(rs.next()){
			Goods vo = new Goods();
			vo.setGid(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getDouble(3));
			vo.setPhoto(rs.getString(4));
			vo.setIid(rs.getInt(5));
			all.add(vo);
			return all;
		}
		return null;
	}
	

	@Override
	public Integer getAllCount() throws SQLException {
		String sql = "select count(*) from goods ";
		super.psmt = super.conn.prepareStatement(sql);
		ResultSet rs = super.psmt.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws SQLException {
		String sql = "select count(*) from goods where" + column + " like? ";
		super.psmt = super.conn.prepareStatement(sql);
		super.psmt.setString(1, "%"+keyWord+"%");
		ResultSet rs = super.psmt.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}
	@Override
	public boolean doRempveBatch(Set<Integer> ids) throws SQLException {
		StringBuffer buf = new StringBuffer();
		buf.append("delete from goods where gid in (");
		Iterator<Integer> iter = ids.iterator();
		while(iter.hasNext()){
			buf.append(iter.next()).append(",");
			
		}
		buf.delete(buf.length()-1,buf.length()).append(")");
		super.psmt = super.conn.prepareStatement(buf.toString());
		return super.psmt.executeUpdate()>0;
		
	}
	
	
	
	

}
