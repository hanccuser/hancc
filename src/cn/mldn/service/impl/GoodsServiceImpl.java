package cn.mldn.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.mldn.dao.IGoodsDAO;
import cn.mldn.dao.IItemDAO;
import cn.mldn.dao.ITagDAO;
import cn.mldn.service.IGoodsService;
import cn.mldn.service.abs.AbstractService;
import cn.mldn.util.factory.Factory;
import cn.mldn.vo.Goods;

public class GoodsServiceImpl extends AbstractService implements IGoodsService {
	public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
	
		Map<String,Object> map = new HashMap<String,Object>();
		IItemDAO itemDAO = Factory.getDAOInstance("item.dao");
		map.put("allItems", itemDAO.findAllForMap());
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		if(column == null||keyWord == null||"".equals(column)||"".equals(keyWord)){
		
			map.put("allGoodss",goodsDAO.findAllSplit(currentPage, lineSize) );
			map.put("goodCount", goodsDAO.getAllCount());
			
		}else{
			map.put("allGoodss",goodsDAO.findAllSplit(currentPage, lineSize, column, keyWord));
			
			map.put("goodCount", goodsDAO.getAllCount(column, keyWord));
			
			
		}
	
		return map;
	}
	@Override
	public Map<String, Object> getAddpre() throws Exception {
		IItemDAO itemDAO = Factory.getDAOInstance("item.dao");
		ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allItems", itemDAO.findAll());
		map.put("allTags",tagDAO.findAll());
		return map;
		
	}

	@Override
	public boolean add(Goods vo, Set<Integer> tids) throws Exception {
		  if(vo.getIid() == null||vo.getIid().equals(0)||vo.getPrice()<=0||vo.getPhoto() == null||"".equals(vo.getPhoto())){
			  return false;
		  }
		  IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		  if(goodsDAO.findByName(vo.getName())== null){
			  if(goodsDAO.doCreate(vo)){
				  
				  Integer gid = goodsDAO.findCreateId();
				  System.out.println(gid);
				  ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
				  tagDAO.doCreatGoodsAndTag(gid, tids);
				  System.out.println("9999");
				  return true;
			  }
		  }
		  return false;
	}
	@Override
	public Map<String, Object> getEditPre(int gid) throws Exception {
		IItemDAO itemDAO = Factory.getDAOInstance("item.dao");
		ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allItems", itemDAO.findAll());
		map.put("allTags",tagDAO.findAll());
		map.put("goods",goodsDAO.findById(gid));
		map.put("goodsTag", tagDAO.findAllByGoods(gid));
		return map;
		
	}
		
	
	@Override
	public boolean edit(Goods vo, Set<Integer> tids) throws Exception {
		 if(vo.getIid() == null||vo.getIid().equals(0)||vo.getPrice()<=0){
			  return false;
		  }
		  IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		  Goods nameGoods = goodsDAO.findByName(vo.getName());
		  if(nameGoods == null||vo.getGid().equals(nameGoods.getGid())){
			  if(goodsDAO.doUpdate(vo)){
				  ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
				  tagDAO.doRemoveGoodsAndTag(vo.getGid());
				  tagDAO.doCreatGoodsAndTag(vo.getGid(), tids);
				  return true;
			  }
		  }
		  return false;
	}
	@Override
	public boolean delete(Set<Integer> ids) throws Exception {
		if(ids == null || ids.size() == 0){
			return false;
		}
		  IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		  return goodsDAO.doRempveBatch(ids);
	}


}
