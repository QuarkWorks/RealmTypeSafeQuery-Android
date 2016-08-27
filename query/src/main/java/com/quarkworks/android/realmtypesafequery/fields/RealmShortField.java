package com.quarkworks.android.realmtypesafequery.fields;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quarkworks.android.realmtypesafequery.BaseRealmField;
import com.quarkworks.android.realmtypesafequery.interfaces.RealmComparableField;
import com.quarkworks.android.realmtypesafequery.interfaces.SortableRealmField;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public class RealmShortField<Model extends RealmModel> extends BaseRealmField<Model, Short>
        implements RealmComparableField<Model, Short>, SortableRealmField<Model, Short> {

    public RealmShortField(@NonNull Class<Model> modelClass, @NonNull String keyPath) {
        super(modelClass, keyPath);
    }

    @Override
    public void equalTo(@NonNull RealmQuery<Model> query, @Nullable Short value) {
        if (value == null) {
            this.isNull(query);
            return;
        }

        query.equalTo(this.getKeyPath(), value);
    }

    @Override
    public void notEqualTo(@NonNull RealmQuery<Model> query, @Nullable Short value) {
        if (value == null) {
            this.isNotNull(query);
            return;
        }

        query.notEqualTo(this.getKeyPath(), value);
    }

    @Override
    public void never(@NonNull RealmQuery<Model> query) {
        query.equalTo(this.getKeyPath(), (short) 0).equalTo(this.getKeyPath(), (short) 1);
    }

    @Override
    public void greaterThan(@NonNull RealmQuery<Model> query, @Nullable Short value) {
        if (value == null) {
            this.notEqualTo(query, null);
            return;
        }

        query.greaterThan(this.getKeyPath(), value);
    }

    @Override
    public void greaterThanOrEqualTo(@NonNull RealmQuery<Model> query, @Nullable Short value) {
        if (value == null) {
            this.equalTo(query, null);
            return;
        }

        query.greaterThanOrEqualTo(this.getKeyPath(), value);
    }

    @Override
    public void lessThan(@NonNull RealmQuery<Model> query, @Nullable Short value) {
        if (value == null) {
            this.notEqualTo(query, null);
            return;
        }

        query.lessThan(this.getKeyPath(), value);
    }

    @Override
    public void lessThanOrEqualTo(@NonNull RealmQuery<Model> query, @Nullable Short value) {
        if (value == null) {
            this.equalTo(query, null);
            return;
        }

        query.lessThanOrEqualTo(this.getKeyPath(), value);
    }

    @Override
    public void between(@NonNull RealmQuery<Model> query, @Nullable Short start, @Nullable Short end) {
        if (start == null || end == null) {
            this.equalTo(query, null);
        }

        query.between(this.getKeyPath(), start, end);
    }
}
