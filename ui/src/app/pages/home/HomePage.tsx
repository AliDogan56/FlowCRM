import authService from "../../services/auth/AuthService";
import {CardTitle, CardText, CardImg, Row, Col, Card, CardBody, Button} from "reactstrap";
import {appRoutes} from "../../../routes";
import {useEffect} from "react";
import notificationService from "../../services/notification/NotificationService";
import {Instagram} from "react-feather";

const HomePage = () => {
    const isAdminOrOwner = authService.isSystemAdminOrOwner();
    const user = localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token') ?? "") : "";
    const jwtBody = user.token.split('.')[1];
    const userPrefix = jwtBody.substring(jwtBody.length - 13, jwtBody.length - 1);


    useEffect(() => {
        notificationService.connect();
        return () => {
            notificationService.disconnect();
        };
    }, []);


    return <>
        <div>
            <Row className={"match-height"}>
                {isAdminOrOwner &&
                    <Col onClick={() => {
                        window.location.href = appRoutes.user.systemowner.list
                    }}>
                        <Card>
                            <CardImg variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                            <CardBody>
                                <CardTitle><h3>System Owner</h3></CardTitle>
                                <CardText>
                                    Info About System Owner
                                </CardText>
                            </CardBody>
                        </Card>
                    </Col>
                }

                {isAdminOrOwner &&
                    <Col onClick={() => {
                        window.location.href = appRoutes.user.systemadmin.list
                    }}>
                        <Card>
                            <CardImg variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                            <CardBody>
                                <CardTitle><h3>System Admins</h3></CardTitle>
                                <CardText>
                                    List of System Admins
                                </CardText>
                            </CardBody>
                        </Card>
                    </Col>
                }
                <Col onClick={() => {
                    window.location.href = appRoutes.user.customer.list
                }}>
                    <Card>
                        <CardImg variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                        <CardBody>
                            <CardTitle><h3>Customer</h3></CardTitle>
                            <CardText>
                                List of Customers
                            </CardText>
                        </CardBody>
                    </Card>
                </Col>
            </Row>
            <Button
                className="mr-1 mb-1"
                color="danger"
                onClick={(value) => {
                    notificationService.sendMessage({
                        userPrefix: userPrefix,
                        message: "WebSocket"
                    }).then((res) => {
                    })
                }}
            >
                <Instagram size={14}/>
            </Button>
        </div>

    </>
}
export default HomePage