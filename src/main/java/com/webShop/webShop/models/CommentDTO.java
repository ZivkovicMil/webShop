package com.webShop.webShop.models;

import com.webShop.webShop.entities.Comment;
import com.webShop.webShop.entities.Product;

public class CommentDTO {

    private String comment;
    private String product_name;

    public CommentDTO() {
    }

    public CommentDTO(CommentDTOBuilder commentDTOBuilder) {
        this.comment = commentDTOBuilder.comment;
        this.product_name = commentDTOBuilder.product_name;
    }

    public CommentDTO(String comment, String product_name) {
        this.comment = comment;
        this.product_name = product_name;
    }

    public String getComment() {
        return comment;
    }

    public String getProduct_name() {
        return product_name;
    }

    public static class CommentDTOBuilder {
        private String comment;
        private String product_name;

        public CommentDTOBuilder() {
        }

        public CommentDTOBuilder setComment(Comment comment) {
            this.comment = comment.getComment_content();
            return this;
        }

        public CommentDTOBuilder setProductName(Product product) {
            this.product_name = product.getName();
            return this;
        }

        public CommentDTO build() {
            CommentDTO commentDTO = new CommentDTO(this);
            return commentDTO;
        }
    }
}
