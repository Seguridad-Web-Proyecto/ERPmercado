package jsf.clas;

import entidades.Ordencompra;
import jsf.clas.util.JsfUtil;
import jsf.clas.util.PaginationHelper;
import bean.sesion.OrdencompraFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("ordencompraController")
@SessionScoped
public class OrdencompraController implements Serializable {

    private Ordencompra current;
    private DataModel items = null;
    @EJB
    private bean.sesion.OrdencompraFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public OrdencompraController() {
    }

    public Ordencompra getSelected() {
        if (current == null) {
            current = new Ordencompra();
            selectedItemIndex = -1;
        }
        return current;
    }

    private OrdencompraFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Ordencompra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Ordencompra();
        selectedItemIndex = -1;
        return "List";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage("¡Orden de compra creada con exito!");
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("¡Lo sentimos la operación no pudo completarse intente mas tarde!");
            return "List";
        }
    }

    public String prepareEdit() {
        current = (Ordencompra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("¡Orden de compra editada con exito!");
            return "List";
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("¡Lo sentimos la operación no pudo completarse intente mas tarde!");
            return "List";
        }
    }

    public String destroy() {
        current = (Ordencompra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage("¡Orden de compra eliminada con exito!");

        } catch (Exception e) {
            JsfUtil.addSuccessMessage("¡Lo sentimos la operación no pudo completarse intente mas tarde!");
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Ordencompra getOrdencompra(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Ordencompra.class)
    public static class OrdencompraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrdencompraController controller = (OrdencompraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ordencompraController");
            return controller.getOrdencompra(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Ordencompra) {
                Ordencompra o = (Ordencompra) object;
                return getStringKey(o.getOrdencompraid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Ordencompra.class.getName());
            }
        }

    }

}
