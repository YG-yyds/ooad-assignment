function createHotel(hotelId, name, city, district, date, time, price, type) {
    var obj = new Object();
    obj.hotelId = hotelId;
    obj.name = name;
    obj.city = city;
    obj.district = district;
    obj.date = date;
    obj.time = time;
    obj.price = price;
    obj.type = type;
    return obj;
};

var hotels = [];
var hotelId = 0;

function del() {
    var tr = this.parentNode.parentNode;
    tr.parentNode.removeChild(tr);
    for (var i = 0; i < hotels.length; i++) {
        if (this.id == hotels[i].hotelId) {
            hotels.splice(i, 1);
            break;
        }
    }
}

function checkValid(name, city, district, date, time, price, type) {
    var reg = /^[A-Za-z]+$/;
    if (!reg.test(name)) {
        alert("Invalid Hotel Name");
        return false;
    }
    if (city == "unselected") {
        alert("No City Selected");
        return false;
    }
    if (district == "0") {
        alert("No District Selected");
        return false;
    }
    if (date == '') {
        alert("The Date Is Not Selected");
        return false;
    } else {
        var dateConvert = new Date(date);
        var currentTime = new Date();
        var currentDate = new Date(currentTime.getFullYear(), currentTime.getMonth(), currentTime.getDate(), 8);
        if (currentDate.getTime() >= dateConvert.getTime()) {
            alert("The Selected Date Is Not After The Current Date");
            return false;
        }
    }
    if (time == '') {
        alert("The Earlist Check-in Time Is Not Selected");
        return false;
    }
    if (price == '' || parseInt(price) < 0) {
        alert("Invalid Price");
        return false;
    }
    if (type == '0') {
        alert("Room Type Is Not Selected");
        return false;
    }
    for (var i = 0; i < hotels.length; i++) {
        var hotel = hotels[i];
        if (hotel.name == name && hotel.city == city && hotel.district == district) {
            if (hotel.type == type) {
                alert("There Is Already A Row With The Same Hotel Name, City, District, Room Type");
                return false;
            } else {
                if (hotel.price == price) {
                    alert("The Same Price With Different Room Type");
                    return false;
                }
            }
        }
    }
    return true;
};

var addShow = document.getElementById("add-show");
var formContainer = document.getElementsByClassName("form-container")[0];
var close = document.getElementById("close");
addShow.onclick = function () {
    formContainer.style.display = "inline";
}
close.onclick = function () {
    formContainer.style.display = "none";
}

var addHotel = document.getElementById("add");
var gz = document.getElementById("gz");
var sz = document.getElementById("sz");
var district = document.getElementById("district");
var city = "unselected";
gz.onclick = function () {
    district.innerHTML = "<option value='0' disabled selected style='display:none;'></option>" +
        "<option>YUE XIU</option>" +
        "<option>HAI ZHU</option>" +
        "<option>LI WAN</option>" +
        "<option>TIAN HE</option>" +
        "<option>BAI YUN</option>" +
        "<option>HUANG PU</option>" +
        "<option>NAN SHA</option>" +
        "<option>PAN YU</option>" +
        "<option>HUA DU</option>" +
        "<option>ZENG CHENG</option>" +
        "<option>CONG HUA</option>";
    city = "GUANG ZHOU";
};
sz.onclick = function () {
    district.innerHTML = "<option value='0' disabled selected style='display:none;'></option>" +
        "<option>FU TIAN</option>" +
        "<option>NAN SHAN</option>" +
        "<option>LUO HU</option>" +
        "<option>LONG GANG</option>" +
        "<option>PING SHAN</option>" +
        "<option>LONG HUA</option>" +
        "<option>GUANG MING</option>" +
        "<option>YAN TIAN</option>";
    city = "SHEN ZHEN"
};
addHotel.onclick = function () {
    var hotelName = document.querySelector('form input[name="hotel-name"]').value;
    var date = document.querySelector('form input[name="date"]').value;
    var time = document.querySelector('form input[name="time"]').value;
    var price = document.querySelector('form input[name="price"]').value;
    var type = document.getElementById("type").value;
    if (checkValid(hotelName, city, district.value, date, time, price, type)) {
        var tr = document.createElement("tr");
        tr.innerHTML = "<td>" + hotelName + "</td>" +
            "<td>" + city + "</td>" +
            "<td>" + district.value + "</td>" +
            "<td>" + date + "</td>" +
            "<td>" + time + "</td>" +
            "<td>" + price + "</td>" +
            "<td>" + type + "</td>" +
            "<td><button class='delete' id='" + hotelId + "'>Delete</button></td>";
        var button = tr.getElementsByTagName("button")[0];
        button.onclick = del;
        var tbody = document.getElementsByTagName("tbody")[0];
        tbody.appendChild(tr);
        var hotel = createHotel(hotelId, hotelName, city, district.value, date, time, price, type);
        hotels.push(hotel);
        hotelId++;
        formContainer.style.display = "none";
    }
};