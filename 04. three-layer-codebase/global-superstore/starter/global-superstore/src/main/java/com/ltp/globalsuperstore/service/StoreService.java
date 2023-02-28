package com.ltp.globalsuperstore.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ltp.globalsuperstore.constant.Constants;
import com.ltp.globalsuperstore.model.Item;
import com.ltp.globalsuperstore.repository.StoreRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class StoreService {

    private StoreRepository repository = new StoreRepository();

    public Item getItem(int index) {
        return repository.getItem(index);
    }

    public void addItem(Item item) {
        repository.addItem(item);
    }

    public void updateItem(Item item, int index) {
        repository.updateItem(item, index);
    }

    public List<Item> getItems() {
        return repository.getItems();
    }

    public int getItemIndex(String id) {
        for (int i = 0; i < getItems().size(); i++)
            if (getItems().get(i).getId().equals(id))
                return i;

        return Constants.NOT_FOUND;
    }

    public Item getItemById(String id) {
        int index = getItemIndex(id);

        return index == Constants.NOT_FOUND ? new Item() : getItem(index);

    }

    public void submitItem(Item item, RedirectAttributes redirectAttributes) {

        int index = getItemIndex(item.getId());

        String status = Constants.SUCCESS_STATUS;

        if (index == Constants.NOT_FOUND) {
            addItem(item);
        } else if (within5Days(item.getDate(), getItem(index).getDate())) {
            updateItem(item, index);
        } else {
            status = Constants.FAILED_STATUS;
        }

        redirectAttributes.addFlashAttribute("status", status);

    }

    private boolean within5Days(Date newDate, Date oldDate) {

        long diff = Math.abs(newDate.getTime() - oldDate.getTime());

        return (int) (TimeUnit.MILLISECONDS.toDays(diff)) <= 5;
    }
}
