export const LocationListElement = ({ location }) => {
    const { name, open, rating } = location;

    return (
        <div>
            <div>{name}</div>
            <div>{open ? 'Abierto' : 'Cerrado'}</div>
            <div>Rating: {rating}/5</div>
        </div>
    )
};
