package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Store;
import com.salesianostriana.edu.proyecto.repositorio.StoreRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class StoreServicio extends BaseService<Store, Long, StoreRepository> {


    public StoreServicio(StoreRepository repo) {
        super(repo);
    }

    public Store findByRuta(String ruta){

        Store obj = new Store();
        for(Store s : this.findAll()){
            if(s.getRuta().equals(ruta)){
                obj=s;
            }
        }

        return obj;
    }


}
