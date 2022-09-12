CREATE TABLE public.sn_profile (
      profile_id UUID NOT NULL,
      "name" varchar(100) NOT NULL UNIQUE ,
      identifier varchar(100) NOT NULL UNIQUE,
      product varchar(50),
      front_prep_data varchar(50),
      prep_data varchar(50),
      append_data varchar(50),
      max_req_size integer,
      pad_length integer,
      pad_characters varchar(1),
      format varchar(12) NOT NULL,
      lower_case_alph boolean,
      exclude_lower_alph varchar(26),
      upper_case_alph boolean,
      exclude_upper_alph varchar(26),
      numeric_values boolean,
      exclude_numeric_values varchar(10),
      special_case boolean,
      exclude_special_case varchar(20),
      active boolean NOT NULL,
      remaining_numbers integer NOT NULL,
      exclude_number_count integer NOT NULL,
      minimum_value integer NOT NULL,
      maximum_value integer NOT NULL,
      profile_creation_date timestamptz NOT NULL,
      is_delete boolean NOT NULL,
      serial_num_chars varchar(82) NOT NULL,
      remarks varchar(400) NOT NULL,
      profile_metadata json NULL,
      CONSTRAINT snprofile_pk PRIMARY KEY (profile_id)
);

CREATE TABLE public.serial_numbers (
                                   id numeric NOT NULL,
                                   serial_number varchar(30) NOT NULL UNIQUE ,
                                   CONSTRAINT serial_nubmer_pk PRIMARY KEY (id)
);


delete from public.serial_numbers;
commit;