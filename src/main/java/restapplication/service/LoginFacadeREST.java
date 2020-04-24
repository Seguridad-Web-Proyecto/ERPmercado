/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapplication.service;

import entidades.Usuariosw;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jcami
 */
@Stateless
public class LoginFacadeREST extends AbstractFacade <Usuariosw>{

    @PersistenceContext(unitName = "com.mycompany_Proveedoressw_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public LoginFacadeREST() {
        super(Usuariosw.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
