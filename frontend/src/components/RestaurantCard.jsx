
export const RestaurantCard = ({ restaurant }) => {
    const { name, open, rating } = restaurant;

    return (
        <div className="restaurant-card card">
            <div className="restaurant-name">{name}</div>
            <div className="restaurant-open">{open ? 'Open' : 'Closed'}</div>
            <div className="restaurant-rating">Rating: {rating}</div>
        </div>
    )
};
