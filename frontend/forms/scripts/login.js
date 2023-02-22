console.log("jai sri ram!");

import navbar from "../../components/nav.js";

let catchElement = (val) => {
    return document.querySelector(val);
};

let createElement = (val) => {
    return document.createElement(val);
};

catchElement('.navbar').innerHTML = navbar();

let getHello = async () => {

    try {
        let res = await fetch("http://localhost:8880/customer/hello");
        let data = await res.json();
        console.log(data);
    } catch (error) {
        console.log(error);
    }
}
getHello();