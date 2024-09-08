const UserListPage = () => {
    const userType = new URL(window.location.href).pathname.toString().split("/")[2];
    return <>
        <div>
            {userType}
        </div>
    </>
}
export default UserListPage