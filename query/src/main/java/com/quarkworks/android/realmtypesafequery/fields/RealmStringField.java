package com.quarkworks.android.realmtypesafequery.fields;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quarkworks.android.realmtypesafequery.BaseRealmField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmEmptyableField;
import com.quarkworks.android.realmtypesafequery.interfaces.SortableRealmField;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public class RealmStringField<Model extends RealmModel> extends BaseRealmField<Model, String>
        implements SortableRealmField<Model, String>, RealmEmptyableField<Model, String> {

    public RealmStringField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }

    @Override
    public void equalTo(@NonNull RealmQuery<Model> query, @Nullable String value) {
        if (value == null) {
            this.isNull(query);
            return;
        }

        query.equalTo(this.getKeyPath(), value);    }

    @Override
    public void notEqualTo(@NonNull RealmQuery<Model> query, @Nullable String value) {
        if (value == null) {
            this.isNotNull(query);
            return;
        }

        query.notEqualTo(this.getKeyPath(), value);
    }

    @Override
    public void never(@NonNull RealmQuery<Model> query) {
        query.equalTo(this.getKeyPath(), "Q").equalTo(this.getKeyPath(), "W");
    }
}
