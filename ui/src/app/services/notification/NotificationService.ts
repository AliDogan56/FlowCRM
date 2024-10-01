import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import axios from "axios";

let user = localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token') ?? "") : "";
const WEB_SOCKET_URL = window.config.webSocketUrl;
const NOTIFICATION_URL = window.config.notificationUrl;
const jwtBody = user.token.split('.')[1];
const userPrefix = jwtBody.substring(jwtBody.length - 13, jwtBody.length-1);


const socket = new SockJS(`${WEB_SOCKET_URL}`);
const stompClient = Stomp.over(socket);
const headers = {
    Authorization: `Bearer ${user.token}`
};
const connect = () => {

    stompClient.connect(headers, (frame: any) => {
        console.log('Connected: ' + frame);

        stompClient?.subscribe(`/queue/notifications/${userPrefix}`, (message: any) => {
            console.log('Özel bildirim alındı: ', message.body);
        });
    }, (error: string) => {
        console.error('Connection error: ', error);
    });

};

const sendMessage = async (messageData: any) => {
    try {
        const response = await axios.post(`${NOTIFICATION_URL}/notification/send-message`, messageData);

        console.log("Message sent successfully:", response.data);

        return response.data;
    } catch (error: any) {
        console.error("Error sending message:", error.response ? error.response.data : error.message);
        throw error;
    }
};


const disconnect = () => {
    stompClient.disconnect(() => {
        console.log('Disconnected');
    });
}

const notificationService = {
    connect,
    disconnect,
    sendMessage
};


export default notificationService