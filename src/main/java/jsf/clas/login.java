/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.clas;

import dataaccess.UsuarioJpaController;
import entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import jsf.clas.util.JsfUtil;

/**
 *
 * @author jaker
 */
@Named(value = "login")
@SessionScoped
public class login implements Serializable
{

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Proveedoressw_war_1.0-SNAPSHOTPU");

    private UserTransaction utx;

    private final UsuarioJpaController userJpa = new UsuarioJpaController(utx, emf);

    private String usuario, contrasenia;

    public String getUsuario()
    {
        return usuario;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    public String entrar()
    {

        if (usuario.equals("cargo") && contrasenia.equals("cargo"))
        {
            return "/pages/cargo/List";
        } else if (usuario.equals("categoria") && contrasenia.equals("categoria"))
        {
            return "/pages/categoria/List";
        } else if (usuario.equals("cliente") && contrasenia.equals("cliente"))
        {
            return "/pages/cliente/List";
        } else if (usuario.equals("compradetalle") && contrasenia.equals("compradetalle"))
        {
            return "/pages/compradetalle/List";
        } else if (usuario.equals("empleado") && contrasenia.equals("empleado"))
        {
            return "/pages/empleado/List";
        } else if (usuario.equals("facturacompra") && contrasenia.equals("facturacompra"))
        {
            return "/pages/facturacompra/List";
        } else if (usuario.equals("facturaventa") && contrasenia.equals("facturaventa"))
        {
            return "/pages/facturaventa/List";
        } else if (usuario.equals("ganancia") && contrasenia.equals("ganancia"))
        {
            return "/pages/ganancia/List";
        } else if (usuario.equals("historialtrabajo") && contrasenia.equals("historialtrabajo"))
        {
            return "/pages/historialtrabajo/List";
        } else if (usuario.equals("ordencompra") && contrasenia.equals("ordencompra"))
        {
            return "/pages/ordencompra/List";
        } else if (usuario.equals("ordenventa") && contrasenia.equals("ordenventa"))
        {
            return "/pages/ordenventa/List";
        } else if (usuario.equals("pagocompra") && contrasenia.equals("pagocompra"))
        {
            return "/pages/pagocompra/List";
        } else if (usuario.equals("pagoventa") && contrasenia.equals("pagoventa"))
        {
            return "/pages/pagoventa/List";
        } else if (usuario.equals("persona") && contrasenia.equals("persona"))
        {
            return "/pages/persona/List";
        } else if (usuario.equals("producto") && contrasenia.equals("producto"))
        {
            return "/pages/producto/List";
        } else if (usuario.equals("proveedor") && contrasenia.equals("proveedor"))
        {
            return "/pages/proveedor/List";
        } else if (usuario.equals("rol") && contrasenia.equals("rol"))
        {
            return "/pages/rol/List";
        } else if (usuario.equals("tarjetacreditocompra") && contrasenia.equals("tarjetacreditocompra"))
        {
            return "/pages/tarjetacreditocompra/List";
        } else if (usuario.equals("tarjetacreditoventa") && contrasenia.equals("tarjetacreditoventa"))
        {
            return "/pages/tarjetacreditoventa/List";
        } else if (usuario.equals("usuario") && contrasenia.equals("usuario"))
        {
            return "/pages/usuario/List";
        } else if (usuario.equals("usuariosw") && contrasenia.equals("usuariosw"))
        {
            return "/pages/usuariosw/List";
        } else if (usuario.equals("ventadetalle") && contrasenia.equals("ventadetalle"))
        {
            return "/pages/ventadetalle/List";
        } else if (usuario.equals("admin") && contrasenia.equals("admin"))
        {
            return "index";
        }
        JsfUtil.addSuccessMessage("Usuario o contraseña incorrectos. Verifique la información");
        return "login.xhtml";
    }

    public String login()
    {

        Usuario usuario1 = null;
        usuario1 = userJpa.findByUser(this.usuario);
        if (usuario1 == null)
        {
            JsfUtil.addSuccessMessage("Usuario o contraseña incorrectos. Verifique la información");
            return "login.xhtml";
        }
        
        String rolName = usuario1.getRolid().getNombre();
        if (rolName.equals("admin"))
        {
            return "index";
        } else if (rolName.equals("ventas"))
        {
            return "/pages/ordenventa/List";
        }else
        {
            JsfUtil.addSuccessMessage("Ocurrió un error");
            return "login.xhtml";
        }

    }

}
