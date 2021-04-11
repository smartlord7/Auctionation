PGDMP         9                y           TestProj    13.2    13.2 $    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    24651    TestProj    DATABASE     j   CREATE DATABASE "TestProj" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE "TestProj";
                testProj    false            �            1259    24681    auction    TABLE     z  CREATE TABLE public.auction (
    auctionid bigint NOT NULL,
    auctionname character varying(128) NOT NULL,
    description character varying(1024),
    currentbidvaue double precision NOT NULL,
    initalvalue double precision NOT NULL,
    currentstate integer DEFAULT 0 NOT NULL,
    starttimestamp timestamp without time zone NOT NULL,
    expirationtimestamp timestamp without time zone NOT NULL,
    endtimestamp timestamp without time zone NOT NULL,
    createtimestamp timestamp without time zone NOT NULL,
    deletetimestamp timestamp without time zone,
    user_userid bigint NOT NULL,
    item_itemid bigint NOT NULL
);
    DROP TABLE public.auction;
       public         heap    testProj    false            �            1259    24690    bid    TABLE     )  CREATE TABLE public.bid (
    bidid bigint NOT NULL,
    description boolean,
    amount double precision NOT NULL,
    createtimestamp timestamp without time zone NOT NULL,
    deletetimestamp timestamp without time zone,
    auction_auctionid bigint NOT NULL,
    user_userid bigint NOT NULL
);
    DROP TABLE public.bid;
       public         heap    testProj    false            �            1259    24673    category    TABLE     �   CREATE TABLE public.category (
    categoryid bigint NOT NULL,
    categoryname character varying(128),
    description character varying(1024)
);
    DROP TABLE public.category;
       public         heap    testProj    false            �            1259    24695    comment    TABLE       CREATE TABLE public.comment (
    commentid bigint NOT NULL,
    text character varying(1024) NOT NULL,
    createtimestamp timestamp without time zone NOT NULL,
    deletetimestamp timestamp without time zone,
    user_userid bigint NOT NULL,
    auction_auctionid bigint NOT NULL
);
    DROP TABLE public.comment;
       public         heap    testProj    false            �            1259    24662    item    TABLE     �  CREATE TABLE public.item (
    itemid bigint NOT NULL,
    ean character varying(13) NOT NULL,
    itemname character varying(128) NOT NULL,
    description character varying(1024),
    quantity smallint DEFAULT 1 NOT NULL,
    origin character varying(128) NOT NULL,
    createtimestamp timestamp without time zone NOT NULL,
    deletetimestamp timestamp without time zone,
    user_userid bigint NOT NULL
);
    DROP TABLE public.item;
       public         heap    testProj    false            �            1259    24708    item_category    TABLE     p   CREATE TABLE public.item_category (
    item_itemid bigint NOT NULL,
    category_categoryid bigint NOT NULL
);
 !   DROP TABLE public.item_category;
       public         heap    testProj    false            �            1259    24652    role    TABLE     �   CREATE TABLE public.role (
    roleid bigint NOT NULL,
    rolename character varying(128) NOT NULL,
    description character varying(1024)
);
    DROP TABLE public.role;
       public         heap    testProj    false            �            1259    24703    user_auction    TABLE     m   CREATE TABLE public.user_auction (
    user_userid bigint NOT NULL,
    auction_auctionid bigint NOT NULL
);
     DROP TABLE public.user_auction;
       public         heap    testProj    false            �          0    24681    auction 
   TABLE DATA           �   COPY public.auction (auctionid, auctionname, description, currentbidvaue, initalvalue, currentstate, starttimestamp, expirationtimestamp, endtimestamp, createtimestamp, deletetimestamp, user_userid, item_itemid) FROM stdin;
    public          testProj    false    203   H-       �          0    24690    bid 
   TABLE DATA           {   COPY public.bid (bidid, description, amount, createtimestamp, deletetimestamp, auction_auctionid, user_userid) FROM stdin;
    public          testProj    false    204   e-       �          0    24673    category 
   TABLE DATA           I   COPY public.category (categoryid, categoryname, description) FROM stdin;
    public          testProj    false    202   �-       �          0    24695    comment 
   TABLE DATA           t   COPY public.comment (commentid, text, createtimestamp, deletetimestamp, user_userid, auction_auctionid) FROM stdin;
    public          testProj    false    205   �-       �          0    24662    item 
   TABLE DATA           �   COPY public.item (itemid, ean, itemname, description, quantity, origin, createtimestamp, deletetimestamp, user_userid) FROM stdin;
    public          testProj    false    201   �-       �          0    24708    item_category 
   TABLE DATA           I   COPY public.item_category (item_itemid, category_categoryid) FROM stdin;
    public          testProj    false    207   �-       �          0    24652    role 
   TABLE DATA           =   COPY public.role (roleid, rolename, description) FROM stdin;
    public          testProj    false    200   �-       �          0    24703    user_auction 
   TABLE DATA           F   COPY public.user_auction (user_userid, auction_auctionid) FROM stdin;
    public          testProj    false    206   .       N           2606    24689    auction auction_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.auction
    ADD CONSTRAINT auction_pkey PRIMARY KEY (auctionid);
 >   ALTER TABLE ONLY public.auction DROP CONSTRAINT auction_pkey;
       public            testProj    false    203            P           2606    24694    bid bid_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.bid
    ADD CONSTRAINT bid_pkey PRIMARY KEY (bidid);
 6   ALTER TABLE ONLY public.bid DROP CONSTRAINT bid_pkey;
       public            testProj    false    204            L           2606    24680    category category_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (categoryid);
 @   ALTER TABLE ONLY public.category DROP CONSTRAINT category_pkey;
       public            testProj    false    202            R           2606    24702    comment comment_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (commentid);
 >   ALTER TABLE ONLY public.comment DROP CONSTRAINT comment_pkey;
       public            testProj    false    205            V           2606    24712     item_category item_category_pkey 
   CONSTRAINT     |   ALTER TABLE ONLY public.item_category
    ADD CONSTRAINT item_category_pkey PRIMARY KEY (item_itemid, category_categoryid);
 J   ALTER TABLE ONLY public.item_category DROP CONSTRAINT item_category_pkey;
       public            testProj    false    207    207            H           2606    24672    item item_ean_key 
   CONSTRAINT     K   ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_ean_key UNIQUE (ean);
 ;   ALTER TABLE ONLY public.item DROP CONSTRAINT item_ean_key;
       public            testProj    false    201            J           2606    24670    item item_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (itemid);
 8   ALTER TABLE ONLY public.item DROP CONSTRAINT item_pkey;
       public            testProj    false    201            D           2606    24659    role role_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (roleid);
 8   ALTER TABLE ONLY public.role DROP CONSTRAINT role_pkey;
       public            testProj    false    200            F           2606    24661    role role_rolename_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_rolename_key UNIQUE (rolename);
 @   ALTER TABLE ONLY public.role DROP CONSTRAINT role_rolename_key;
       public            testProj    false    200            T           2606    24707    user_auction user_auction_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.user_auction
    ADD CONSTRAINT user_auction_pkey PRIMARY KEY (user_userid, auction_auctionid);
 H   ALTER TABLE ONLY public.user_auction DROP CONSTRAINT user_auction_pkey;
       public            testProj    false    206    206            W           2606    24713    auction auction_fk2    FK CONSTRAINT     y   ALTER TABLE ONLY public.auction
    ADD CONSTRAINT auction_fk2 FOREIGN KEY (item_itemid) REFERENCES public.item(itemid);
 =   ALTER TABLE ONLY public.auction DROP CONSTRAINT auction_fk2;
       public          testProj    false    2890    203    201            X           2606    24718    bid bid_fk1    FK CONSTRAINT     }   ALTER TABLE ONLY public.bid
    ADD CONSTRAINT bid_fk1 FOREIGN KEY (auction_auctionid) REFERENCES public.auction(auctionid);
 5   ALTER TABLE ONLY public.bid DROP CONSTRAINT bid_fk1;
       public          testProj    false    204    2894    203            Y           2606    24723    comment comment_fk2    FK CONSTRAINT     �   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_fk2 FOREIGN KEY (auction_auctionid) REFERENCES public.auction(auctionid);
 =   ALTER TABLE ONLY public.comment DROP CONSTRAINT comment_fk2;
       public          testProj    false    203    2894    205            [           2606    24733    item_category item_category_fk1    FK CONSTRAINT     �   ALTER TABLE ONLY public.item_category
    ADD CONSTRAINT item_category_fk1 FOREIGN KEY (item_itemid) REFERENCES public.item(itemid);
 I   ALTER TABLE ONLY public.item_category DROP CONSTRAINT item_category_fk1;
       public          testProj    false    2890    201    207            \           2606    24738    item_category item_category_fk2    FK CONSTRAINT     �   ALTER TABLE ONLY public.item_category
    ADD CONSTRAINT item_category_fk2 FOREIGN KEY (category_categoryid) REFERENCES public.category(categoryid);
 I   ALTER TABLE ONLY public.item_category DROP CONSTRAINT item_category_fk2;
       public          testProj    false    202    207    2892            Z           2606    24728    user_auction user_auction_fk2    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_auction
    ADD CONSTRAINT user_auction_fk2 FOREIGN KEY (auction_auctionid) REFERENCES public.auction(auctionid);
 G   ALTER TABLE ONLY public.user_auction DROP CONSTRAINT user_auction_fk2;
       public          testProj    false    2894    203    206            �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �     