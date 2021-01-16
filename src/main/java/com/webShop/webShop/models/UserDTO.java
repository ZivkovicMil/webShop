package com.webShop.webShop.models;

public class UserDTO {

    private String email;
    private String first_name;
    private String last_name;
    private String address;
    private String phone_number;
    private boolean is_enabled;

    public UserDTO(UserDTOBuilder userDTOBuilder) {
        this.email = userDTOBuilder.email;
        this.first_name = userDTOBuilder.first_name;
        this.last_name = userDTOBuilder.last_name;
        this.address = userDTOBuilder.address;
        this.phone_number = userDTOBuilder.phone_number;
        this.is_enabled = userDTOBuilder.is_enabled;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public static class UserDTOBuilder {

        private String email;
        private String first_name;
        private String last_name;
        private String address;
        private String phone_number;
        private boolean is_enabled;

        public UserDTOBuilder() {
        }

        public UserDTOBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder setFirstName(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public UserDTOBuilder setLastName(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public UserDTOBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserDTOBuilder setPhoneNUmber(String phone_number) {
            this.phone_number = phone_number;
            return this;
        }

        public UserDTOBuilder isEnabled(boolean is_enabled) {
            this.is_enabled = is_enabled;
            return this;
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO(this);
            return userDTO;
        }
    }

}
