package com.livebarn.sushishop.model;

public enum Status {
        CREATED(1),
        IN_PROGRESS(2),
        PAUSED(3),
        FINISHED(4),
        CANCELLED(5);

        private final Integer statusId;

        Status(Integer statusId) {
                this.statusId = statusId;
        }

        public Integer getStatusId() {
                return this.statusId;
        }
}