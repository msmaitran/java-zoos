package com.lambdaschool.zoos.services;

import com.lambdaschool.zoos.models.Telephone;
import com.lambdaschool.zoos.repository.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelephoneServiceImpl implements TelephoneService {

    @Autowired
    private TelephoneRepository telephonerepos;

    @Override
    public List<Telephone> findAll() {
        List<Telephone> list = new ArrayList<>();
        telephonerepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Telephone findTelephoneById(long id) {
        return telephonerepos.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Telephone with id " + id + " not found!"));
    }

    @Override
    public List<Telephone> findByZooId(long id) {
        return telephonerepos.findAllByZoo_Zooid(id);
    }

    @Override
    public Telephone update(long phoneid, String phonenumber) {
        if (telephonerepos.findById(phoneid).isPresent()) {
            Telephone telephone = findTelephoneById(phoneid);
            telephone.setPhonenumber(phonenumber);
            return telephonerepos.save(telephone);
        } else {
            throw new EntityNotFoundException("Telephone with id " + phoneid + " not found!");
        }
    }

    @Override
    public void delete(long id) {
        if (telephonerepos.findById(id).isPresent()) {
            telephonerepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Telephone with id " + id + " not found!");
        }
    }
}
