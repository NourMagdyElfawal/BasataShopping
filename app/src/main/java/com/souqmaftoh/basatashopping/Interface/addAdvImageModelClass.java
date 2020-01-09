package com.souqmaftoh.basatashopping.Interface;

import android.net.Uri;

public class addAdvImageModelClass {

        private Uri image;
        public addAdvImageModelClass(Uri image) {
            this.image = image;
        }
        public Uri getImage() {
            return image;
        }

    public void setImage(Uri image) {
        this.image = image;
    }

    }
