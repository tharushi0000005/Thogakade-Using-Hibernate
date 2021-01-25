package lk.ijse.pos.hibernate.bo.custom;

import lk.ijse.pos.hibernate.bo.SuperBO;
import lk.ijse.pos.hibernate.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO {
    public boolean add(ItemDTO itemDTO) throws Exception;

    public List<ItemDTO> findAll() throws Exception;

    public boolean delete(String s) throws Exception;

    public ItemDTO getItem(String id)throws Exception;

    public boolean update(ItemDTO itemDTO) throws Exception;
}
