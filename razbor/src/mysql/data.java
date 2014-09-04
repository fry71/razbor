/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mysql;

/**
 *
 * @author fry
 */
class data {
   private int id;
   private String address;

    public data(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public data() {
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
