(ns interface-test.submit-order
  (:use [interface-test.core]
        [interface-test.script]))


(def getwifishourimiandan-product-list
  "wifishourimiandan product list."
  (reduce into (for [tab (:tabList (body (call-default "getwifishourimiandan")))]
                 (for [prod (:productList tab)]
                   prod))))

(for [k (keys (first getwifishourimiandan-product-list))
      :when (> (.indexOf (name k) "Id") 0)]
  k)

(def product-id-list (select-keys-vector getwifishourimiandan-product-list [:supplierRelationId :productId :periodId :destinationId :activityId]))

(def sub-product-list (reduce into (for [prod product-id-list]
                                     (for [city (:cityList (body (getdetailforsubmit prod)))]
                                       (conj prod city)))))

(def sub-product-submit-info-list
  (for [prod sub-product-list]
    (conj (body (getdetailforsubmit prod)) (body (call-default "getpricecalendar" prod)))))

(call-service-json t-req (decrypt-with-hello "xLZoXAmbD4hDoQclvUb+IkFJz68ElVdIU7L2APFS+PTz58QgA/AhqUDKrcj8Lga8Z3VzRu+x7M3hkDSxQpbWDQ=="))
