
INSERT INTO mileage ( user_id, point) VALUES ( "3ede0ef2-92b7-4817-a5f3-0c575361f745",0);

INSERT INTO place ( place_id, place_nm) VALUES ( "2e4baf1c-5acb-4efb-a1af-eddada31b00f", "장소01");

INSERT INTO review (review_id, place_id, review_cts, user_id) VALUES ("240a0658-dc5f-4878-9381-ebb7b2667772", "2e4baf1c-5acb-4efb-a1af-eddada31b00f", "좋아요!", "3ede0ef2-92b7-4817-a5f3-0c575361f745" );

INSERT INTO review_photo (photo_id, review_id, file_path, file_nm) VALUES ("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "240a0658-dc5f-4878-9381-ebb7b2667772", "/image/test/", "test.jpg");

INSERT INTO review_photo (photo_id, review_id, file_path, file_nm) VALUES ("afb0cef2-851d-4a50-bb07-9cc15cbdc332", "240a0658-dc5f-4878-9381-ebb7b2667772", "/image/test/", "test.jpg2");