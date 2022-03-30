package com.example.moviettn.model.response;



public class ProfileResponse {
        private Integer status;
        private Boolean success;

        private User user;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }


}
