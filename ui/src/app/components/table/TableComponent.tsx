import React, {FunctionComponent} from 'react';
import Table from 'react-bootstrap/Table';

interface TableProps {
    columns: any[];
    data: any[];
    showActions: boolean;

}

const TableComponent: FunctionComponent<TableProps> = (props) => {
    const filteredColumns = props.columns.filter(column => {
        return !(!props.showActions && column.id === "actions");
    });


    return (
        <Table striped bordered hover>
            <thead>
            <tr>
                {filteredColumns.map((column, index) => (
                    <th key={index} className={column.className}>{column.Header}</th>
                ))}
            </tr>
            </thead>
            <tbody>
            {props.data.map((row, rowIndex) => (
                <tr key={rowIndex}>
                    {filteredColumns.map((column, colIndex) => (
                        <td key={colIndex} className={column.className}>
                            {column.Cell ? (column.Cell({value: row[column.accessor]})) : (
                                <span>{row[column.accessor]}</span>)}
                        </td>
                    ))}
                </tr>
            ))}
            </tbody>
        </Table>
    );
};

export default TableComponent;
