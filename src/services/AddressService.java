package services;

import dto.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressService {
    private final Map<String, ArrayList<Address>> userAddresses = new HashMap<>();
    public boolean addAddress(String emailID, Address newAddr) {
        if (!userAddresses.containsKey(emailID)) {
            userAddresses.put(emailID, new ArrayList<>());
        }
        userAddresses.get(emailID).add(newAddr);
        return true;
    }
    public ArrayList<Address> getAddressesByEmail(String emailID) {
        ArrayList<Address> addressList = new ArrayList<>();
        if(userAddresses.containsKey(emailID)) {
            addressList = userAddresses.get(emailID);
        }
        return addressList;
    }
}
