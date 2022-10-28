package io.pharmatrace.ptsersnm.context.common;

public class Constants {

    public static final String SHIPMENT_DETAILS = "{\n" +
            "    \"shipments\": [\n" +
            "        {\n" +
            "            \"id\": \"7777777770\",\n" +
            "            \"service\": \"express\",\n" +
            "            \"origin\": {\n" +
            "                \"address\": {\n" +
            "                    \"addressLocality\": \"HONG KONG - HONG KONG SAR, CHINA\"\n" +
            "                },\n" +
            "                \"servicePoint\": {\n" +
            "                    \"url\": \"http://www.dhl.com.hk/en/country_profile.html\",\n" +
            "                    \"label\": \"Origin Service Area\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"destination\": {\n" +
            "                \"address\": {\n" +
            "                    \"addressLocality\": \"TAIPEI - TAIWAN\"\n" +
            "                },\n" +
            "                \"servicePoint\": {\n" +
            "                    \"url\": \"http://www.dhl.com.tw/en/country_profile.html\",\n" +
            "                    \"label\": \"Destination Service Area\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"status\": {\n" +
            "                \"timestamp\": \"2022-10-18T12:25:00\",\n" +
            "                \"location\": {\n" +
            "                    \"address\": {\n" +
            "                        \"addressLocality\": \"NUERNBERG - GERMANY\"\n" +
            "                    }\n" +
            "                },\n" +
            "                \"statusCode\": \"transit\",\n" +
            "                \"status\": \"transit\",\n" +
            "                \"description\": \"Arrived at DHL Delivery Facility NUERNBERG - GERMANY\"\n" +
            "            },\n" +
            "            \"details\": {\n" +
            "                \"proofOfDeliverySignedAvailable\": false,\n" +
            "                \"totalNumberOfPieces\": 4,\n" +
            "                \"pieceIds\": [\n" +
            "                    \"JD014600010360350990\",\n" +
            "                    \"JD014600010531331558\",\n" +
            "                    \"JD014600010531331565\",\n" +
            "                    \"JD014600010531331657\"\n" +
            "                ]\n" +
            "            },\n" +
            "            \"events\": [\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-10-18T12:25:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"NUERNBERG - GERMANY\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Arrived at DHL Delivery Facility NUERNBERG - GERMANY\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-10-03T08:05:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"TAIPEI - TAIWAN\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Customs clearance status updated. Note - The Customs clearance process may start while the shipment is in transit to the destination.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-09-30T08:55:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"TAIPEI - TAIWAN\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Customs clearance status updated. Note - The Customs clearance process may start while the shipment is in transit to the destination.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-09-29T15:33:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"TAIPEI - TAIWAN\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Customs clearance status updated. Note - The Customs clearance process may start while the shipment is in transit to the destination.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-09-29T10:07:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"TAIPEI - TAIWAN\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Customs clearance status updated. Note - The Customs clearance process may start while the shipment is in transit to the destination.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-09-28T16:23:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"TAIPEI - TAIWAN\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Customs clearance status updated. Note - The Customs clearance process may start while the shipment is in transit to the destination.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-09-28T06:51:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"TAIPEI - TAIWAN\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Customs clearance status updated. Note - The Customs clearance process may start while the shipment is in transit to the destination.\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-09-22T15:32:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"HONG KONG - HONG KONG SAR, CHINA\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Shipment information received\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-08-26T09:15:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"WUERZBURG - GERMANY\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Arrived at DHL Delivery Facility WUERZBURG - GERMANY\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-08-24T12:46:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"NUERNBERG - GERMANY\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Arrived at DHL Delivery Facility NUERNBERG - GERMANY\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"timestamp\": \"2022-08-17T15:00:00\",\n" +
            "                    \"location\": {\n" +
            "                        \"address\": {\n" +
            "                            \"addressLocality\": \"NUERNBERG - GERMANY\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"description\": \"Arrived at DHL Delivery Facility NUERNBERG - GERMANY\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
