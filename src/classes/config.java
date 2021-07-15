/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author DESTINY
 */
public class config {
    private static config mSomeConfig = new config();

    private String id = "6";
    private String adminKey ="cc6c26f4160be76a3891570d0fdf1f2c3cf43520";
    
    /**
     *
     * @return id of software
     */
    public static String getID() {
        return mSomeConfig.id;
    }
     public static String getAdminKey() {
        return mSomeConfig.adminKey;
    }
}