-- For compatibility with PostgreSQL
CREATE DOMAIN IF NOT EXISTS TIMESTAMPTZ AS TIMESTAMP;

-- drop table if exists serial_numbers;
-- drop table if exists sn_profile;


CREATE TABLE public.sn_profile (
      profile_id UUID NOT NULL,
      profile_name varchar(100) NOT NULL,
      identifier varchar(100) NOT NULL,
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
      include_special_case varchar(20),
      active boolean NOT NULL,
      remaining_numbers numeric(30) NOT NULL,
      exclude_number_count numeric(30) NOT NULL,
      minimum_value varchar(20) NOT NULL,
      maximum_value varchar(20) NOT NULL,
      profile_creation_date timestamptz NOT NULL,
      is_delete boolean NOT NULL,
      serial_num_chars varchar(82) NOT NULL,
      serial_num_length integer NOT NULL,
      remarks varchar(400)  NULL,
      profile_metadata varchar(1000) NULL,
      serial_number_index integer NOT NULL,
      serial_number_used_index integer NOT NULL,
      CONSTRAINT snprofile_pk PRIMARY KEY (profile_id)
);

CREATE UNIQUE INDEX "profile_name_unique_soft" ON sn_profile(profile_name) WHERE sn_profile.is_delete IS FALSE;
CREATE UNIQUE INDEX "profile_identifier_unique_soft" ON sn_profile(identifier) WHERE sn_profile.is_delete IS FALSE;

CREATE TABLE public.serial_numbers (
                                   sn_id UUID NOT NULL ,
                                   sn_index numeric,
                                   serial_number varchar(30) NOT NULL UNIQUE ,
                                   status varchar(20) NOT NULL default 'AVAILABLE',
                                   profile_id UUID,
                                   CONSTRAINT number_profile_fk FOREIGN KEY (profile_id) REFERENCES sn_profile(profile_id),
                                   CONSTRAINT serial_nubmer_pk PRIMARY KEY (profile_id,serial_number)
);
